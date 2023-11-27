import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import TabBar from './../../../components/TabBar/TabBar';
import Layout from '../../../layouts/Layout';
import CustomTable from '../../../components/Table/CustomTable';
import { BarChart } from '@mui/x-charts/BarChart';

const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['대여/반납 이력', '대여소 랭킹'];
const outerTab = 'manage';
const innerTab = 'rental-ranking';
const url = {
  'rental-history': '/user/manage/rental-history',
  'rental-ranking': '/user/manage/rental-ranking'
};

const head = ['등수', '아이디', '이용 거리', '이용 횟수'];
const body = [
  { name: '1', calories: 159, fat: 6.0, carbs: 24 },
  {
    name: '2',
    calories: 237,
    fat: 9.0,
    carbs: 37
  },
  { name: '3', calories: 262, fat: 16.0, carbs: 24 }
];

const UserManageRentalRanking = () => {
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <h3 style={{}}>나의 랭킹</h3>
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
          series={[
            { data: [10, 9, 8, 7, 6, 5, 4, 3, 2, 1] } // 각 사람의 데이터 값을 순서대로 배열로 전달합니다.
          ]}
          width={500}
          height={300}
        />
        <h3 style={{ float: 'left' }}>전체 랭킹 목록(이용 거리 순)</h3>
        <CustomTable headData={head} bodyData={body} />
      </Article>
    </Layout>
  );
};
export default UserManageRentalRanking;
