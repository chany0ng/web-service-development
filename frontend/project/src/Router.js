import { Routes, Route, Link, Redirect } from 'react-router-dom';
import LandingPage from './pages/Login/LandingPage';
import SignInPage from './pages/Login/SignInPage';
import Header from './layouts/Header';
import Footer from './layouts/Footer';
import SignUpPage from './pages/Login/SignUpPage';
import FindPwPage from './pages/Login/FindPwPage';

const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/signin" element={<SignInPage />} />
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/findpw" element={<FindPwPage />} />
    </Routes>
  );
};

export default Router;
