import Layout from '../../../layouts/Layout';
import {} from 'react-naver-maps';
import { useEffect, useState, useRef } from 'react';
import { getFetch, postFetch } from '../../../config';
import WhereToVoteTwoToneIcon from '@mui/icons-material/WhereToVoteTwoTone';
const UserMap = () => {
  const [data, setData] = useState([]);
  const mapRef = useRef(null);
  const markerArr = [];
  const [locaionInfo, setLocationInfo] = useState({});
  const { naver } = window;
  const contentString = [
    '<div class="iw_inner">',
    '   <h3>서울특별시청</h3>',
    '   <p>서울특별시 중구 태평로1가 31 | 서울특별시 중구 세종대로 110 서울특별시청<br>',
    '       <img src="./img/hi-seoul.jpg" width="55" height="55" alt="서울시청" class="thumb" /><br>',
    '       02-120 | 공공,사회기관 > 특별,광역시청<br>',

    '   </p>',
    '</div>'
  ].join('');
  const infowindow = new naver.maps.InfoWindow({
    content: contentString,
    maxWidth: 200,
    backgroundColor: '#ffffff',
    borderColor: '#2db400',
    borderWidth: 2,
    anchorSize: new naver.maps.Size(20, 20),
    anchorSkew: true,
    anchorColor: '#ffffff',

    pixelOffset: new naver.maps.Point(20, -20)
  });
  const getLocationInfo = async (data) => {
    try {
      console.log(data);
      const response = await postFetch('api/map/info', data);
      if (response.status === 200) {
        const data = await response.json();
        console.log(data, data.bike);
        setLocationInfo({ ...data });
      } else {
        throw new Error('조회소 상세 로딩 에러');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  useEffect(() => {
    const location = new naver.maps.LatLng(37.619774, 127.060926);
    const mapOptions = {
      mapTypeId: naver.maps.MapTypeId.TERRAIN,
      zoom: 16,
      zoomControl: true,
      center: location
    };
    mapRef.current = new naver.maps.Map('map', mapOptions);
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
      return location.address.includes('노원');
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
            `<div style='width: 30px; height: 30px; background-color: #008800; border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-size: 15px; font-weight: bold;, cursor: pointer'>${location.bikeCount}</div>`
          ].join(''),
          size: new naver.maps.Size(38, 58),
          anchor: new naver.maps.Point(15, 15)
        }
      });
      markerArr.push(marker);
      naver.maps.Event.addListener(
        marker,
        'click',
        function (e) {
          const [latitude, longitude] = marker.title.split(',');
          getLocationInfo({ latitude, longitude });
          if (infowindow.getMap()) {
            infowindow.close();
          } else {
            infowindow.open(mapRef.current, marker);
          }
        },
        []
      );
    });
  }, [data]);
  return (
    <Layout>
      <div
        id="map"
        style={{ height: '80vh', width: '60vw', margin: '0 auto' }}
      />
    </Layout>
  );
};
export default UserMap;
