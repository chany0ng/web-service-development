import React from 'react';
import { Link } from 'react-router-dom';
import Button from '@mui/material/Button';
import './css/LandingPage.module.min.css';
import LoginBackground from '../components/Background/LoginBackground';

const LandingPage = () => {
  return (
    <LoginBackground>
      <h1>
        따릉이와 함께하는 건강한 도시
        <br />
        세계적인 자전거 도시, 서울
      </h1>
      <Link to="/signin" className="link">
        <Button
          type="button"
          variant="contained"
          style={{ borderRadius: '10px', fontSize: '10px' }}
          className="btn"
        >
          Start
        </Button>
      </Link>
    </LoginBackground>
  );
};

export default LandingPage;
