import styles from './InnerTabBar.module.scss';
import { useNavigate, Link } from 'react-router-dom';
import { useState } from 'react';

const InnerTabBar = ({ title }) => {
  const navigate = useNavigate();
  const [selectedTab, setSelectedTab] = useState(1);
  const tabClickHandler = (num) => {
    if (selectedTab !== num) {
      navigationHandler(num);
    }
  };
  const navigationHandler = (num) => {
    setSelectedTab(num);
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
          selectedTab === 1 ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(1)}
      >
        <h2>{title[0]}</h2>
      </div>
      <div
        className={`${
          selectedTab === 2 ? styles.activeTab : styles['flex-item']
        }`}
        onClick={() => tabClickHandler(2)}
      >
        <h2>{title[1]}</h2>
      </div>
    </div>
  );
};

export default InnerTabBar;
