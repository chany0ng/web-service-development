import Layout from '../../../layouts/Layout';
import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import styles from './UserInfoBookMark.module.scss';
import { Card, Container, Button } from '@mui/material';
import { useState, useEffect } from 'react';
import CustomTable from '../../../components/Table/CustomTable';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { useNavigate } from 'react-router-dom';
import { getFetch, postFetch } from '../../../config';
const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['개인정보 수정', '대여소 즐겨찾기'];
const outerTab = 'info';
const innerTab = 'bookmark';
const url = { edit: '/user/info/edit', bookmark: '/user/info/bookmark' }; // 테이블에 넘길 값
const head = [
  '대여소 고유번호',
  '대여소 주소',
  '대여소 위도 정보',
  '대여소 경도 정보',
  '대여소 상태'
];
const body = [
  { name: '1', calories: 159, fat: 6.0, carbs: 24, protein: '이용 가능' },
  {
    name: '2',
    calories: 237,
    fat: 9.0,
    carbs: 37,
    protein: '이용 가능'
  }
];

const UserInfoBookMark = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [station, setStation] = useState('');
  const [favorites, SetFavorites] = useState({});
  const inputStationHandler = (e) => {
    e.preventDefault();
    const newStation = e.target.value;
    setStation(newStation);
  };
  const addStationHandler = async (e) => {
    try {
      e.preventDefault();
      console.log(station);
      const response = await postFetch('api/favorites/list', {
        location: station
      });
      if (response.status === 200) {
        const data = await response.json();
        console.log(data);
      } else {
        throw new Error('즐겨찾기 대여소 조회 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  useEffect(() => {
    const getFavoritesList = async () => {
      try {
        const response = await getFetch('api/favorites/list');
        if (response.status === 200) {
          const data = await response.json();
          console.log(data);
        } else {
          throw new Error('즐겨찾기 대여소 리스트 조회 오류');
        }
      } catch (error) {
        console.error(error);
        alert(error);
      }
    };
    getFavoritesList();
  }, []);
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <Container maxWidth="xs">
          <Card
            variant="outlined"
            sx={{
              borderColor: 'primary.main',
              padding: '0px',
              borderRadius: '5px'
            }}
            className={styles.card}
          >
            <h2>대여소 즐겨찾기 추가</h2>
            <div className={styles.favorite}>
              <input
                type="text"
                name="location_id"
                id="location_id"
                value={station}
                onChange={inputStationHandler}
                placeholder="대여소 이름"
              />
              <Button
                variant="contained"
                color="primary"
                sx={{ fontSize: '1rem' }}
                onClick={addStationHandler}
              >
                검색하기
              </Button>
            </div>
          </Card>
        </Container>
        <h2 style={{ textAlign: 'center', marginTop: '50px' }}>
          즐겨찾는 대여소 목록
        </h2>
        <CustomTable headData={head} bodyData={body} />
      </Article>
    </Layout>
  );
};

export default UserInfoBookMark;
