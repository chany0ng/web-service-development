import React from 'react';
import { Link } from 'react-router-dom';
import Button from '@mui/material/Button';
import './css/LandingPage.module.min.css';

const LandingPage = () => {
  return (
    <div className="box">
      <div className="background-image">
        <h1>
          자전거와 함께하는 건강한 도시
          <br />
          세계적인 자전거 도시 서울
        </h1>
        <Link to="/signin" className="link">
          <Button type="button" variant="contained">
            Start
          </Button>
        </Link>
      </div>
    </div>
  );
};

export default LandingPage;
