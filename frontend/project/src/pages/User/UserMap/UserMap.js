import Layout from '../../../layouts/Layout';
import {} from 'react-naver-maps';
import { useEffect, useState, useRef } from 'react';
import { getFetch, postFetch } from '../../../config';
import styles from './UserMap.module.scss';
import StarBorderIcon from '@mui/icons-material/StarBorder';
import StarIcon from '@mui/icons-material/Star';
import { Button } from '@mui/material';
import { useRecoilState } from 'recoil';
import { userInfo } from './../../../recoil';
import { useNavigate } from 'react-router-dom';
const UserMap = () => {
  const [user, setUser] = useRecoilState(userInfo);
  const [data, setData] = useState([]);
  const mapRef = useRef(null);
  const markerArr = [];
  const [locationInfo, setLocationInfo] = useState({});
  const [isClicked, setIsClicked] = useState(false);
  const { naver } = window;
  const navigate = useNavigate();
  const setFavorite = async () => {
    if (locationInfo.favorite) {
      const response = await postFetch('api/favorites/change', {
        location: locationInfo.address,
        favorite: false
      });
      if (response.status === 200) {
        alert('즐겨찾기가 해제되었습니다');
      } else if (response.status === 401) {
        navigate('/');
      } else throw new Error('즐겨찾기 변경 에러');
    }
    if (!locationInfo.favorite) {
      const response = await postFetch('api/favorites/change', {
        location: locationInfo.address,
        favorite: true
      });
      if (response.status === 200) {
        alert('즐겨찾기가 추가되었습니다');
      } else if (response.status === 401) {
        navigate('/');
      } else throw new Error('즐겨찾기 변경 에러');
    }
  };
  const returnBikeHandler = async (e) => {
    try {
      e.preventDefault();
      const response = await postFetch('api/return', {
        bike_id: user.bike_id,
        end_location: locationInfo.location_id
      });
      if (response.status === 200) {
        const data = await response.json();
        if (data.overfee > 0) {
          setUser((prev) => ({
            ...prev,
            isRented: 0,
            bike_id: null,
            cash: 0,
            overfee: data.overfee
          }));
          alert(
            `따릉이 반납 완료. 추가 요금 ${data.overfee}를 결제해야 합니다!`
          );
          navigate('user/payment/extra-chrage');
        } else {
          setUser((prev) => ({
            ...prev,
            isRented: 0,
            bike_id: null,
            cash: prev.cash - data.withdraw
          }));
          alert('따릉이 반납 완료');
        }
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('따릉이 반납 실패!');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  const rentBikeHandler = async (bikeNumber) => {
    try {
      const response = await postFetch('api/rent', {
        bike_id: bikeNumber,
        start_location: locationInfo.location_id
      });
      if (response.status === 200) {
        setUser((prev) => ({
          ...prev,
          isRented: 1,
          bike_id: bikeNumber,
          hour: 0
        }));
        alert(`${bikeNumber} 따릉이를 대여했습니다`);
      } else if (response.status === 400) {
        alert('이용권을 먼저 구매해주세요');
      } else if (response.status === 401) {
        navigate('/');
      } else if (response.status === 402) {
        alert(`미납 추가요금을 먼저 결제해주세요`);
      } else {
        throw new Error('따릉이 대여 실패!');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  const headLine = (
    <div
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '5px'
      }}
    >
      <h2 style={{ marginRight: '10px' }}>
        {locationInfo.location_id}.{locationInfo.address}
      </h2>
      {locationInfo.favorite === true ? (
        <StarIcon></StarIcon>
      ) : (
        <StarBorderIcon></StarBorderIcon>
      )}
      <p
        style={{ fontSize: '1.1rem', cursor: 'pointer' }}
        onClick={setFavorite}
      >
        즐겨찾기
      </p>
      {user.isRented && (
        <Button
          variant="contained"
          color="primary"
          onClick={returnBikeHandler}
          sx={{ fontSize: '1rem', marginLeft: '10px' }}
        >
          반납하기
        </Button>
      )}
    </div>
  );
  useEffect(() => {
    const location = new naver.maps.LatLng(37.619774, 127.060926);
    const mapOptions = {
      mapTypeId: naver.maps.MapTypeId.TERRAIN,
      zoom: 16,
      zoomControl: true,
      center: location
    };
    const bicycleLayer = new naver.maps.BicycleLayer();
    mapRef.current = new naver.maps.Map('map', mapOptions);
    naver.maps.Event.once(mapRef.current, 'init', function () {
      bicycleLayer.setMap(mapRef.current);
    });
  }, []);
  useEffect(() => {
    const getLocationInfo = async () => {
      try {
        const response = await getFetch('api/map');
        if (response.status === 200) {
          const data = await response.json();
          setData(data.locations);
        } else {
          throw new Error('대여소 정보 로딩 에러');
        }
      } catch (error) {
        alert(error);
        console.error(error);
      }
    };
    getLocationInfo();
  }, [mapRef]);
  useEffect(() => {
    const filtered = data.filter((location) => {
      return (
        location.address.includes('노원') ||
        location.address.includes('중랑') ||
        location.address.includes('동대문') ||
        location.address.includes('성북')
      );
    });
    filtered.map((location) => {
      const marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(location.latitude, location.longitude),
        map: mapRef.current,
        title: `${location.latitude},${location.longitude}`,
        clickable: true,
        cursor: 'pointer',
        animation: naver.maps.Animation.DROP,
        icon: {
          content: [
            `<div style='width: 30px; height: 30px; background-color: #008800; border-radius: 50%;  display: flex; align-items: center; justify-content: center; color: white; font-size: 15px; font-weight: bold;, cursor: pointer'>${location.bikeCount}</div>`
          ].join(''),
          size: new naver.maps.Size(38, 58),
          anchor: new naver.maps.Point(15, 15)
        }
      });
      markerArr.push(marker);
      const getLocationDetailInfo = async (data) => {
        try {
          const response = await postFetch('api/map/info', data);
          if (response.status === 200) {
            const data = await response.json();
            setLocationInfo((prev) => ({ ...prev, ...data }));
          } else {
            throw new Error('조회소 상세 로딩 에러');
          }
        } catch (error) {
          alert(error);
          console.error(error);
        }
      };
      naver.maps.Event.addListener(marker, 'click', function (e) {
        setIsClicked((prev) => !prev);
        const [latitude, longitude] = marker.title.split(',');
        getLocationDetailInfo({ latitude, longitude });
        const contentString = [
          '<div style="padding: 5px">',
          `   <h3>${location.address}</h3>`,
          '</div>'
        ].join('');
        const infowindow = new naver.maps.InfoWindow({
          content: contentString,
          maxWidth: 150,
          backgroundColor: '#ffffff',
          borderColor: '#2db400',
          borderWidth: 2,
          anchorSize: new naver.maps.Size(20, 20),
          anchorSkew: true,
          anchorColor: '#ffffff',
          pixelOffset: new naver.maps.Point(20, -20)
        });
        if (infowindow.getMap()) {
          infowindow.close();
        } else {
          infowindow.open(mapRef.current, marker);
        }
      });
    });
  }, [data]);
  return (
    <Layout>
      <div
        id="map"
        style={{
          height: '80vh',
          width: '60vw',
          margin: '0 auto'
        }}
      >
        {isClicked && (
          <div className={styles.box}>
            {headLine}
            {locationInfo?.bike?.map((item, index) => (
              <div className={styles.row} key={index}>
                일반 따릉이 {item.bike_id}
                {!user.isRented && (
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => {
                      rentBikeHandler(item.bike_id);
                    }}
                    sx={{ fontSize: '1rem', marginLeft: '20%' }}
                  >
                    대여하기
                  </Button>
                )}
              </div>
            ))}
          </div>
        )}
      </div>
    </Layout>
  );
};
export default UserMap;
