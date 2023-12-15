import Layout from '../../../layouts/Layout';
import Article from '../../../layouts/Article';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import styles from './AdminMangeBike.module.scss';
import { useNavigate, useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { getFetch, postFetch } from '../../../config';
import CustomTable from '../../../components/Table/CustomTable';
import { Button } from '@mui/material';
import CustomPagination from '../../../components/Pagination/CustomPagination';
const innerTitle = ['따릉이 추가/삭제', '대여소 개설/폐쇄'];
const innerTab = 'location';
const url = {
  bike: '/admin/manage/bike/1',
  location: '/admin/manage/location/1'
};
const headData = [
  '대여소 번호',
  '대여소 주소',
  '비치 자전거 수',
  '대여소 삭제'
];
const AdminManageLocation = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const { pageNumber } = useParams();
  const [currentPage, setCurrentPage] = useState(
    pageNumber ? parseInt(pageNumber, 10) : 1
  );
  const [locationCount, setLocationCount] = useState(1);
  const [locationList, setLocationList] = useState([]);
  const [inputData, setSearchId] = useState({
    location_id: '',
    address: '',
    latitude: '',
    longitude: ''
  });
  const inputIdHandler = (e) => {
    e.preventDefault();
    const id = e.target.id;
    const value = e.target.value;
    setSearchId((prev) => ({
      ...prev,
      [id]: value
    }));
  };
  const searchHandler = async () => {
    try {
      const response = await postFetch('api/admin/location/create', inputData);
      console.log(inputData);

      if (response.status === 200) {
        alert('대여소가 추가되었습니다!');
        window.location.reload();
      } else if (response.status === 401) {
        navigate('/');
      } else if (response.status === 409) {
        alert('이미 존재하는 대여소입니다!');
      } else {
        throw new Error('대여소 추가 오류');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  // 첫 접속 & 페이지 변경 시 마다 실행
  const handlePageChange = (e, newPage) => {
    setCurrentPage(newPage);
  };
  const bikeDeleteHandler = async (bike_id) => {
    try {
      const response = await postFetch('api/admin/bike/delete', {
        bike_id: bike_id
      });
      if (response.status === 200) {
        alert('따릉이가 삭제되었습니다');
        window.location.reload();
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('따릉이 삭제 오류');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  useEffect(() => {
    navigate(`/admin/manage/location/${currentPage}`);
  }, [currentPage]);
  useEffect(() => {
    // page가 바뀌면, 실행되는 함수
    const fetchLocationList = async (pageNumber) => {
      try {
        const response = await getFetch(
          `api/admin/location/list/${pageNumber}`
        );
        if (response.status === 200) {
          const data = await response.json();
          setLocationCount(data.locationCount);
          const formattedData = data.locationList.map((location) => {
            return {
              ...location,
              button: (
                <Button
                  variant="contained"
                  color="primary"
                  sx={{ fontSize: '1rem' }}
                  onClick={() => {
                    bikeDeleteHandler(location.location_id);
                  }}
                >
                  삭제하기
                </Button>
              )
            };
          });
          setLocationList(formattedData);
        } else if (response.status === 401) {
          navigate('/');
        } else {
          throw new Error('대여소 정보 로딩 에러');
        }
      } catch (error) {
        console.error(error);
        alert(error);
      }
    };
    fetchLocationList(currentPage);
  }, [currentPage]);
  return (
    <Layout admin="true">
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <h1
          style={{ textAlign: 'left', fontSize: '2.5rem', marginBottom: '5vh' }}
        >
          새로운 대여소 추가하기
        </h1>
        <div className={styles.search}>
          <input
            type="text"
            name="location_id"
            id="location_id"
            value={inputData.location_id}
            onChange={inputIdHandler}
            placeholder="대여소 번호"
          />
          <input
            type="text"
            name="address"
            id="address"
            value={inputData.address}
            onChange={inputIdHandler}
            style={{ width: '15vw' }}
            placeholder="대여소 주소"
          />
          <input
            type="text"
            name="latitude"
            id="latitude"
            value={inputData.latitude}
            onChange={inputIdHandler}
            placeholder="위도"
          />
          <input
            type="text"
            name="longitude"
            id="longitude"
            value={inputData.longitude}
            onChange={inputIdHandler}
            placeholder="경도"
          />
          <Button
            variant="contained"
            color="primary"
            sx={{ fontSize: '1rem' }}
            onClick={searchHandler}
          >
            추가하기
          </Button>
        </div>
        <h1
          style={{ textAlign: 'left', fontSize: '2.5rem', marginBottom: '5vh' }}
        >
          대여소 정보 조회
        </h1>
        <CustomTable bodyData={locationList} headData={headData} />
        <CustomPagination
          page={currentPage}
          totalPages={locationCount}
          setPage={handlePageChange}
        />
      </Article>
    </Layout>
  );
};
export default AdminManageLocation;
