import { Routes, Route, Link, Redirect } from 'react-router-dom';
import LandingPage from './pages/Login/LandingPage';
import SignInPage from './pages/Login/SignInPage';
import SignUpPage from './pages/Login/SignUpPage';
import FindPwPage from './pages/Login/FindPwPage';
import UserMainPage from './pages/User/UserMainPage';
const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/signin" element={<SignInPage />} />
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/findpw" element={<FindPwPage />} />
      <Route path="/user/main" element={<UserMainPage />} />
    </Routes>
  );
};

export default Router;
