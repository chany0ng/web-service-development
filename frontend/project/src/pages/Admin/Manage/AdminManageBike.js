import Layout from '../../../layouts/Layout';
import Article from '../../../layouts/Article';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import { Button } from '@mui/material';
import styles from './AdminMangeBike.module.scss';
import { useNavigate, useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { getFetch, postFetch } from '../../../config';
import CustomTable from '../../../components/Table/CustomTable';
import CustomPagination from '../../../components/Pagination/CustomPagination';
const innerTitle = ['따릉이 추가/삭제', '대여소 개설/폐쇄'];
const innerTab = 'bike';
const url = {
  bike: '/admin/manage/bike/1',
  location: '/admin/manage/location/1'
};
const headData = [
  '따릉이 번호',
  '따릉이가 위치한 대여소 주소',
  '따릉이 상태',
  '따릉이 삭제'
];

const AdminManageBike = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const { pageNumber } = useParams();
  const [currentPage, setCurrentPage] = useState(
    pageNumber ? parseInt(pageNumber, 10) : 1
  );
  const [bikeCount, setBikeCount] = useState(1);
  const [bikeList, setBikeList] = useState([]);
  const [inputData, setSearchId] = useState({
    bike_id: '',
    location_id: ''
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
  // 첫 접속 & 페이지 변경 시 마다 실행
  const handlePageChange = (e, newPage) => {
    setCurrentPage(newPage);
  };
  const searchHandler = async () => {
    try {
      const response = await postFetch('api/admin/bike/create', inputData);
      console.log(inputData);
      if (response.status === 200) {
        alert('따릉이가 추가되었습니다!');
        window.location.reload();
      } else if (response.status === 401) {
        navigate('/');
      } else if (response.status === 404) {
        alert('존재하지 않는 대여소 번호입니다');
      } else if (response.status === 409) {
        alert('이미 존재하는 자전거입니다!');
      } else {
        throw new Error('따릉이 추가 오류');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
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
    navigate(`/admin/manage/bike/${currentPage}`);
  }, [currentPage]);
  useEffect(() => {
    // page가 바뀌면, 실행되는 함수
    const fetchUserList = async (pageNumber) => {
      try {
        const response = await getFetch(`api/admin/bike/list/${pageNumber}`);
        if (response.status === 200) {
          const data = await response.json();
          setBikeCount(data.bikeCount);
          const formattedData = data.bikeList.map((bike) => {
            return {
              ...bike,
              button: (
                <Button
                  variant="contained"
                  color="primary"
                  sx={{ fontSize: '1rem' }}
                  onClick={() => {
                    bikeDeleteHandler(bike.bike_id);
                  }}
                >
                  삭제하기
                </Button>
              )
            };
          });
          setBikeList(formattedData);
        } else if (response.status === 401) {
          navigate('/');
        } else {
          throw new Error('사용자 정보 로딩 에러');
        }
      } catch (error) {
        console.error(error);
        alert(error);
      }
    };
    fetchUserList(currentPage);
  }, [currentPage]);
  return (
    <Layout admin="true">
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <h1
          style={{ textAlign: 'left', fontSize: '2.5rem', marginBottom: '5vh' }}
        >
          새로운 따릉이 추가하기
        </h1>
        <div className={styles.search}>
          <input
            type="text"
            name="bike_id"
            id="bike_id"
            value={inputData.bike_id}
            onChange={inputIdHandler}
            style={{ width: '15vw' }}
            placeholder="자전거 번호"
          />
          <input
            type="text"
            name="location_id"
            id="location_id"
            value={inputData.location_id}
            onChange={inputIdHandler}
            style={{ width: '15vw' }}
            placeholder="대여소 번호"
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
        <h1 style={{ textAlign: 'left', fontSize: '2.5rem' }}>
          따릉이 정보 조회
        </h1>
        <CustomTable bodyData={bikeList} headData={headData} />
        <CustomPagination
          page={currentPage}
          totalPages={bikeCount}
          setPage={handlePageChange}
        />
      </Article>
    </Layout>
  );
};
export default AdminManageBike;
