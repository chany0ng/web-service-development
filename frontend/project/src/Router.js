import { Routes, Route } from 'react-router-dom';
import LandingPage from './pages/Login/LandingPage';
import SignInPage from './pages/Login/SignInPage';
import SignUpPage from './pages/Login/SignUpPage';
import FindPwPage from './pages/Login/FindPwPage';
import UserMainPage from './pages/User/UserMainPage';
import UserInfoEdit from './pages/User/UserInfo/UserInfoEdit';
import UserInfoBookMark from './pages/User/UserInfo/UserInfoBookMark';
import UserPayCharge from './pages/User/UserPay/UserPayCharge';
import UserPayExtraCharge from './pages/User/UserPay/UserPayExtraCharge';
import UserManageRentalHistory from './pages/User/UserManage/UserManageRentalHistory';
import UserManageRentalRanking from './pages/User/UserManage/UserManageRentalRanking';
import UserTicketBuy from './pages/User/UserTicket/UserTicketBuy';
import UserTicketGift from './pages/User/UserTicket/UserTicketGift';
import UserNoticeList from './pages/User/UserNotice/UserNoticeList';
import UserNoticeView from './pages/User/UserNotice/UserNoticeView';
import UserReportList from './pages/User/UserReport/UserReportList';
import UserReportView from './pages/User/UserReport/UserReportView';
import UserReportWrite from './pages/User/UserReport/UserReportWrite';
const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/signin" element={<SignInPage />} />
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/findpw" element={<FindPwPage />} />
      <Route path="/user/main" element={<UserMainPage />} />
      <Route path="/user/info/edit" element={<UserInfoEdit />} />
      <Route path="/user/info/bookmark" element={<UserInfoBookMark />} />
      <Route path="/user/payment/charge" element={<UserPayCharge />} />
      <Route
        path="/user/payment/extra-charge"
        element={<UserPayExtraCharge />}
      />
      <Route
        path="/user/manage/rental-history"
        element={<UserManageRentalHistory />}
      />
      <Route
        path="/user/manage/rental-ranking"
        element={<UserManageRentalRanking />}
      />
      <Route path="/user/tickets/purchase" element={<UserTicketBuy />} />
      <Route path="/user/tickets/gift" element={<UserTicketGift />} />
      <Route
        path="/user/notice/noticeList/:pageNumber"
        element={<UserNoticeList />}
      />
      <Route
        path="/user/notice/noticeView/:postNumber"
        element={<UserNoticeView />}
      />
      <Route
        path="/user/report/reportList/:pageNumber"
        element={<UserReportList />}
      />
      <Route
        path="/user/report/reportView/:postNumber"
        element={<UserReportView />}
      />
      <Route
        path="/user/report/reportBoardEdit"
        element={<UserReportWrite />}
      />
    </Routes>
  );
};

export default Router;
