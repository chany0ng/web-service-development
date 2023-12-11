import Layout from '../../../layouts/Layout';
import {} from 'react-naver-maps';
import { useEffect, useState, useRef } from 'react';
import { getFetch, postFetch } from '../../../config';
import WhereToVoteTwoToneIcon from '@mui/icons-material/WhereToVoteTwoTone';
const UserMap = () => {
  const [data, setData] = useState([]);
  const mapRef = useRef(null);
  const markerArr = [];
  const { naver } = window;
  const getLocationInfo = async (data) => {
    try {
      console.log(data);
      const response = await postFetch('api/map/info', data);
      if (response === 200) {
        const data = await response.json();
        console.log(data, data.bike);
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
          console.log(latitude, longitude);
          getLocationInfo({ latitude, longitude });
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
