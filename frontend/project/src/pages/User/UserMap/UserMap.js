import Layout from '../../../layouts/Layout';
import Article from './../../../layouts/Article';
import {} from 'react-naver-maps';
import { useEffect } from 'react';
import { getFetch } from '../../../config';
const UserMap = () => {
  const { naver } = window;
  useEffect(() => {
    const getLocationInfo = async () => {
      try {
        const response = await getFetch('api/map');
        if (response.status === 200) {
          const data = await response.json();
          console.log(data.locations);
        } else {
          throw new Error('대여소 정보 로딩 에러');
        }
      } catch (error) {
        alert(error);
        console.error(error);
      }
    };
    getLocationInfo();
  }, []);
  useEffect(() => {
    const location = new naver.maps.LatLng(37.619774, 127.060926);
    const mapOptions = {
      mapTypeId: naver.maps.MapTypeId.TERRAIN,
      zoomControl: true,
      mapTypeControl: true,
      center: location
    };
    const map = new naver.maps.Map('map', mapOptions);
    new naver.maps.Marker({
      position: location,
      map
    });
  }, []);
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
