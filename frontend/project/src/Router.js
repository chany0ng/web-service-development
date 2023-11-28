import { Routes, Route } from 'react-router-dom';
import { LoginPageAuthCheck, MainPageAuthCheck } from './AuthCheck';
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

const Router = () => {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <LoginPageAuthCheck>
            <LandingPage />
          </LoginPageAuthCheck>
        }
      />
      <Route
        path="/signin"
        element={
          <LoginPageAuthCheck>
            <SignInPage />
          </LoginPageAuthCheck>
        }
      />
      <Route
        path="/signup"
        element={
          <LoginPageAuthCheck>
            <SignUpPage />
          </LoginPageAuthCheck>
        }
      />
      <Route
        path="/findpw"
        element={
          <LoginPageAuthCheck>
            <FindPwPage />
          </LoginPageAuthCheck>
        }
      />
      <Route path="/user/main" element={<UserMainPage />} />
      <Route path="/user/info/edit" element={<UserInfoEdit />} />
      <Route path="/user/info/bookmark" element={<UserInfoBookMark />} />
      <Route path="/user/pay/charge" element={<UserPayCharge />} />
      <Route path="/user/pay/extra-charge" element={<UserPayExtraCharge />} />
      <Route
        path="/user/manage/rental-history"
        element={<UserManageRentalHistory />}
      />
      <Route
        path="/user/manage/rental-ranking"
        element={<UserManageRentalRanking />}
      />
    </Routes>
  );
};

export default Router;
{
  /* <Route path="/" element={<LoginPageAuthCheck><LandingPage /></LoginPageAuthCheck>} />
<Route path="/signin" element={<LoginPageAuthCheck><SignInPage /></LoginPageAuthCheck>} />
<Route path="/signup" element={<LoginPageAuthCheck><SignUpPage /></LoginPageAuthCheck>} />
<Route path="/findpw" element={<LoginPageAuthCheck><FindPwPage /></LoginPageAuthCheck>} />
<Route path="/user/main" element={<MainPageAuthCheck><UserMainPage /></MainPageAuthCheck>} />
<Route path="/user/info/edit" element={<MainPageAuthCheck><UserInfoEdit /></MainPageAuthCheck>} />
<Route path="/user/info/bookmark" element={<MainPageAuthCheck><UserInfoBookMark /></MainPageAuthCheck>} />
<Route path="/user/pay/charge" element={<MainPageAuthCheck><UserPayCharge /></MainPageAuthCheck>} />
<Route path="/user/pay/extra-charge" element={<MainPageAuthCheck><UserPayExtraCharge /></MainPageAuthCheck>} />
<Route path="/user/manage/rental-history" element={<MainPageAuthCheck><UserManageRentalHistory /></MainPageAuthCheck>} />
<Route path="/user/manage/rental-ranking" element={<MainPageAuthCheck><UserManageRentalRanking /></MainPageAuthCheck>} /> */
}
