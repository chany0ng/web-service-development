import styles from './InnerTabBar.module.scss';
import { useNavigate, Link } from 'react-router-dom';
import { useState } from 'react';

const InnerTabBar = ({ title, select, url }) => {
  const urlKeys = Object.keys(url);
  const navigate = useNavigate();
  const tabClickHandler = (num) => {
    if (num === 0) {
      console.log(url[urlKeys[num]]);
      navigate(url[urlKeys[num]]);
    }
    if (num === 1) {
      console.log(url[select]);
      navigate(url[urlKeys[num]]);
    }
  };
  return (
    <div className={styles['flex-container']}>
      <div
        className={`${
          select === urlKeys[0] ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(0)}
      >
        <h2>{title[0]}</h2>
      </div>
      <div
        className={`${
          select === urlKeys[1] ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(1)}
      >
        <h2>{title[1]}</h2>
      </div>
    </div>
  );
};

export default InnerTabBar;
