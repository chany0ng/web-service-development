import styles from './InnerTabBar.module.scss';
import { useNavigate, Link } from 'react-router-dom';
import { useState } from 'react';

const InnerTabBar = ({ title, select }) => {
  const navigate = useNavigate();
  const tabClickHandler = (num) => {
    if (num === 1) {
      navigate('/user/info/edit');
    }
    if (num === 2) {
      navigate('/user/info/bookmark');
    }
  };
  return (
    <div className={styles['flex-container']}>
      <div
        className={`${
          select === 'edit' ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(1)}
      >
        <h2>{title[0]}</h2>
      </div>
      <div
        className={`${
          select === 'bookmark' ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(2)}
      >
        <h2>{title[1]}</h2>
      </div>
    </div>
  );
};

export default InnerTabBar;
