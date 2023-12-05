import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import TabBar from './../../../components/TabBar/TabBar';
import Layout from '../../../layouts/Layout';
import dayjs from 'dayjs';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { useState, useEffect } from 'react';
import { Button } from '@mui/material';
import CustomTable from '../../../components/Table/CustomTable';
import { useNavigate } from 'react-router-dom';
import { mainPageAuthCheck } from '../../../AuthCheck';
const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['대여/반납 이력', '대여소 랭킹'];
const outerTab = 'manage';
const innerTab = 'rental-history';
const url = {
  'rental-history': '/user/manage/rental-history',
  'rental-ranking': '/user/manage/rental-ranking'
};
const head = [
  '자전거 고유번호',
  '대여 시각',
  '대여소',
  '반납 시각',
  '반납 대여소',
  '자전거 대여 시간'
];
const body = [];
const UserManageRentalHistory = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [startDate, setStartDate] = useState(dayjs());
  const [endDate, setEndDate] = useState(dayjs());
  const handleStartDateChange = (date) => {
    setStartDate(date);
    //todo 날짜 데이터 저장형식 정해야함(toDate()고려)
  };

  const handleEndDateChange = (date) => {
    setEndDate(date);
    //todo 날짜 데이터 저장형식 정해야함(toDate()고려)
  };
  const onSubmitHandler = (e) => {
    e.preventDefault();
    alert('날짜 제출');
  };
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={['DatePicker', 'DatePicker']}
            sx={{
              dispaly: 'flex',
              alignItems: 'center',
              justifyContent: 'center'
            }}
          >
            <DatePicker
              label="시작 날짜"
              value={startDate}
              format="YYYY / MM / DD"
              onChange={handleStartDateChange}
            />
            <span style={{ fontSize: '2rem' }}>~</span>
            <DatePicker
              label="마지막 날짜"
              value={endDate}
              format="YYYY / MM / DD"
              onChange={handleEndDateChange}
            />
            <Button
              onClick={onSubmitHandler}
              type="submit"
              variant="contained"
              sx={{
                mt: 3,
                width: '10vw',
                padding: '12px',
                fontSize: '1.2rem',
                backgroundColor: 'secondary.main',
                '&:hover': {
                  backgroundColor: 'secondary.main'
                }
              }}
            >
              검색
            </Button>
          </DemoContainer>
        </LocalizationProvider>
        <CustomTable headData={head} bodyData={body} />
      </Article>
    </Layout>
  );
};

export default UserManageRentalHistory;
