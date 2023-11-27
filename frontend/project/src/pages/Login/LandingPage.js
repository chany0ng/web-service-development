import React from 'react';
import { Link } from 'react-router-dom';
import Button from '@mui/material/Button';
import styles from './LandingPage.module.scss';
import LoginBackground from '../../components/Background/LoginBackground';

const LandingPage = () => {
  return (
    <LoginBackground>
      <h1 className={styles.h1}>
        따릉이와 함께하는 건강한 도시
        <br />
        세계적인 자전거 도시, 서울
      </h1>
      <Link to="/signin" className={styles.link}>
        <Button
          type="button"
          variant="contained"
          style={{ borderRadius: '10px', fontSize: '10px' }}
          className={styles.btn}
        >
          Start
        </Button>
      </Link>
    </LoginBackground>
  );
};

export default LandingPage;
