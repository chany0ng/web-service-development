import Layout from '../../../layouts/Layout';
import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import styles from './UserInfoBookMark.module.scss';
import { Card, Container, Button } from '@mui/material';
import { useState } from 'react';
import CustomTable from '../../../components/Table/CustomTable';

const innerTitle = ['개인정보 수정', '대여소 즐겨찾기'];
const outerTab = 'info';
const innerTab = 'bookmark';

// 테이블에 넘길 값
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
  },
  { name: '3', calories: 262, fat: 16.0, carbs: 24, protein: '이용 가능' },
  { name: '4', calories: 305, fat: 3.7, carbs: 67, protein: '이용 가능' },
  { name: '5', calories: 356, fat: 16.0, carbs: 49, protein: '폐쇄' }
];

const UserInfoBookMark = () => {
  const [station, setStation] = useState('');
  const inputStationHandler = (e) => {
    e.preventDefault();
    const newStation = e.target.value;
    setStation(newStation);
  };
  const addStationHandler = (e) => {
    e.preventDefault();
    // api요청하기
    // 1. 정상: 받아온 데이터로 즐겨찾기에 추가
    // 2. 비정상: alert처리
  };
  return (
    <Layout>
      <TabBar title={innerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} />
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
                placeholder="대여소 고유번호"
              />
              <Button
                variant="contained"
                color="primary"
                sx={{ fontSize: '1rem' }}
                onClick={addStationHandler}
              >
                추가하기 +
              </Button>
            </div>
          </Card>
        </Container>
        <CustomTable headData={head} bodyData={body} />
      </Article>
    </Layout>
  );
};

export default UserInfoBookMark;
