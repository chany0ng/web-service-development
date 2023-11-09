import { Routes, Route, Link, Redirect } from 'react-router-dom';
import SignInPage from './pages/SignInPage';
import Header from './layouts/Header';
import Footer from './layouts/Footer';
import SignUpPage from './pages/SignUpPage';

const Router = () => {
  return (
    <Routes>
      <Route path="/signin" element={<SignInPage />} />
      <Route
        path="/signup"
        element={
          <>
            <SignUpPage />
            <Footer />
          </>
        }
      />
    </Routes>
  );
};

export default Router;
