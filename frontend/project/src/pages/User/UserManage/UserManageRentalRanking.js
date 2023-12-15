import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import TabBar from './../../../components/TabBar/TabBar';
import Layout from '../../../layouts/Layout';
import CustomTable from '../../../components/Table/CustomTable';
import { BarChart } from '@mui/x-charts/BarChart';
import styles from './UserManageRentalRanking.module.scss';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { getFetch } from '../../../config';
const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['대여/반납 이력', '대여소 랭킹'];
const outerTab = 'manage';
const innerTab = 'rental-ranking';
const url = {
  'rental-history': '/user/manage/rental-history',
  'rental-ranking': '/user/manage/rental-ranking'
};

const UserManageRentalRanking = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [timeRankTotal, setTimeRankTotal] = useState({});
  const [timeRankId, setTimeRankId] = useState([]);
  const [timeRankData, setTimeRankData] = useState([]);
  const [countRankTotal, setCountRankTotal] = useState({});
  const [countRankId, setCountRankId] = useState([]);
  const [countRankData, setCountRankData] = useState([]);
  useEffect(() => {
    const getTimeRank = async () => {
      try {
        const response = await getFetch('api/rank/time');
        if (response.status === 200) {
          const data = await response.json();
          setTimeRankTotal((prev) => ({ ...prev, ...data }));
          const ids = data.rank.map((data) => data.user_id);
          const datas = data.rank.map((data) => data.duration_time);
          setTimeRankId(ids);
          setTimeRankData(datas);
        } else if (response.status === 401) {
          navigate('/');
        } else {
          throw new Error('대여시간 랭킹 로딩 에러');
        }
      } catch (error) {
        alert(error);
        console.error(error);
      }
    };
    const getCountRank = async () => {
      try {
        const response = await getFetch('api/rank/count');
        if (response.status === 200) {
          const data = await response.json();
          setCountRankTotal((prev) => ({ ...prev, ...data }));
          const ids = data.rank.map((data) => {
            return data.user_id;
          });
          const datas = data.rank.map((data) => {
            return data.using_count;
          });
          setCountRankId(ids);
          setCountRankData(datas);
        } else if (response.status === 401) {
          navigate('/');
        } else {
          throw new Error('대여횟수 랭킹 로딩 에러');
        }
      } catch (error) {
        alert(error);
        console.error(error);
      }
    };
    getTimeRank();
    getCountRank();
  }, []);
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <h2 style={{ marginBottom: '10px' }}>나의 대여 시간 랭킹</h2>
        <div className={styles.myRank}>
          <span>순위: {timeRankTotal.user_ranking}등</span>
          <span>ID: {timeRankTotal.user_id}</span>
          <span>시간: {timeRankTotal.user_duraiton_time}분</span>
        </div>
        <h2>대여 시간 순 차트</h2>
        {timeRankId}
        {timeRankId.length > 0 && timeRankData.length > 0 && (
          <BarChart
            xAxis={[
              {
                scaleType: 'band',
                data: timeRankId
              }
            ]}
            series={[{ data: timeRankData.map(Number) }]}
            height={300}
          />
        )}
        <br />
        <h2 style={{ marginBottom: '10px', marginTop: '50px' }}>
          나의 대여 횟수 랭킹
        </h2>
        <div className={styles.myRank}>
          <span>순위: {countRankTotal.user_ranking}등</span>
          <span>ID: {countRankTotal.user_id}</span>
          <span>횟수: {countRankTotal.user_using_count}회</span>
        </div>
        <h2>이용 횟수 순 차트</h2>
        {countRankId.length > 0 && countRankData.length > 0 && (
          <BarChart
            xAxis={[
              {
                scaleType: 'band',
                data: countRankId
              }
            ]}
            series={[{ data: countRankData.map(Number) }]}
            height={300}
          />
        )}
      </Article>
    </Layout>
  );
};
export default UserManageRentalRanking;
