import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import TabBar from './../../../components/TabBar/TabBar';
import Layout from '../../../layouts/Layout';
import CustomTable from '../../../components/Table/CustomTable';
import { BarChart } from '@mui/x-charts/BarChart';
import styles from './UserManageRentalRanking.module.scss';
import { useEffect } from 'react';
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

const head = ['등수', '아이디', '이용 거리', '이용 횟수'];
const body = {
  user_ranking: 12345,
  user_id: 'string',
  user_duraiton_time: 'string',
  rank: [
    {
      ranking: 1,
      user_id: 'string1',
      duration_time: 5
    },
    {
      ranking: 2,
      user_id: 'string2',
      duration_time: 5
    },
    {
      ranking: 3,
      user_id: 'string3',
      duration_time: 5
    },
    {
      ranking: 4,
      user_id: 'string4',
      duration_time: 5
    },
    {
      ranking: 5,
      user_id: 'string5',
      duration_time: 5
    },
    {
      ranking: 6,
      user_id: 'string6',
      duration_time: 5
    },
    {
      ranking: 7,
      user_id: 'string7',
      duration_time: 5
    },
    {
      ranking: 8,
      user_id: 'string8',
      duration_time: 5
    },
    {
      ranking: 9,
      user_id: 'string9',
      duration_time: 5
    },
    {
      ranking: 10,
      user_id: 'string10',
      duration_time: 5
    }
  ]
};
const idData = body.rank.map((data) => {
  return data.user_id;
});
const timeData = body.rank.map((data) => {
  return data.duration_time;
});
const UserManageRentalRanking = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  let timeRankId = [];
  let timeRankData = [];
  let countRankId = [];
  let countRankData = [];
  const getTimeRank = async () => {
    try {
      const response = await getFetch('api/rank/time');
      if (response.status === 200) {
        const data = await response.json();
        timeRankId = data.rank.map((data) => {
          return data.user_id;
        });
        timeRankData = data.rank.map((data) => {
          return data.duration_time;
        });
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
        countRankId = data.rank.map((data) => {
          return data.user_id;
        });
        countRankData = data.rank.map((data) => {
          return data.user_using_count;
        });
      } else {
        throw new Error('대여횟수 랭킹 로딩 에러');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  useEffect(() => {
    getCountRank();
    getTimeRank();
  }, []);
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <h3 style={{ marginBottom: '10px' }}>나의 대여 시간 랭킹</h3>
        <div className={styles.myRank}>
          <span>{timeRankData.user_ranking}등</span>
          <span>{timeRankData.user_id}</span>
          <span>{timeRankData.user_duraiton_time}</span>
        </div>
        <h2>대여 시간 순 차트</h2>
        <BarChart
          xAxis={[
            {
              scaleType: 'band',
              data: idData
            }
          ]}
          series={[{ data: timeData }]}
          height={300}
        />
        <br />
        <h3 style={{ marginBottom: '10px' }}>나의 대여 횟수 랭킹</h3>
        <div className={styles.myRank}>
          <span>{countRankData.user_ranking}등</span>
          <span>{countRankData.user_id}</span>
          <span>{countRankData.user_using_count}</span>
        </div>
        <h2>이용 횟수 순 차트</h2>
        <BarChart
          xAxis={[
            {
              scaleType: 'band',
              data: [
                '사람1',
                '사람2',
                '사람3',
                '사람4',
                '사람5',
                '사람6',
                '사람7',
                '사람8',
                '사람9',
                '사람10'
              ]
            }
          ]}
          series={[{ data: [10, 9, 8, 7, 6, 5, 4, 3, 2, 1] }]}
          height={300}
        />
        {/* <h3 style={{ float: 'left' }}>전체 랭킹 목록(이용 거리 순)</h3>
        <CustomTable headData={head} bodyData={body} /> */}
      </Article>
    </Layout>
  );
};
export default UserManageRentalRanking;
