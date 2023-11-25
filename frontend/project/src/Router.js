import { Routes, Route, Link, Redirect } from 'react-router-dom';
import LandingPage from './pages/Login/LandingPage';
import SignInPage from './pages/Login/SignInPage';
import SignUpPage from './pages/Login/SignUpPage';
import FindPwPage from './pages/Login/FindPwPage';
import UserMainPage from './pages/User/UserMainPage';
import UserInfoEdit from './pages/User/UserInfo/UserInfoEdit';
import UserInfoBookMark from './pages/User/UserInfo/UserInfoBookMark';
import UserPayCharge from './pages/User/UserPay/UserPayCharge';
import UserPayExtraCharge from './pages/User/UserPay/UserPayExtraCharge';
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
      <Route path="/user/pay/charge" element={<UserPayCharge />} />
      <Route path="/user/pay/extra-charge" element={<UserPayExtraCharge />} />
    </Routes>
  );
};

export default Router;
