import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import styles from './TabBar.module.scss';
import PersonIcon from '@mui/icons-material/Person';
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import EditNoteIcon from '@mui/icons-material/EditNote';

const TabBar = ({ title, select }) => {
  const navigate = useNavigate();
  const tabClickHandler = (num) => {
    if (num === 1) navigate('/user/info/edit');
    if (num === 2) navigate('/user/pay/charge');
    if (num === 3) navigate('/user/main');
  };
  return (
    <div className={styles['flex-container']}>
      <div
        className={`${
          select === 'info' ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(1)}
      >
        <PersonIcon sx={{ fontSize: '25px' }} />
        <div>{title[0]}</div>
      </div>
      <div
        className={`${
          select === 'pay' ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(2)}
      >
        <MonetizationOnIcon sx={{ fontSize: '25px' }} /> <div>{title[1]}</div>
      </div>
      <div
        className={`${
          select === 'manage' ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(3)}
      >
        <EditNoteIcon sx={{ fontSize: '25px' }} />
        <div>{title[2]}</div>
      </div>
    </div>
  );
};

export default TabBar;
