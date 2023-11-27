import styles from './InnerTabBar.module.scss';
import { useNavigate } from 'react-router-dom';

const InnerTabBar = ({ title, select, url }) => {
  const urlKeys = Object.keys(url);
  const navigate = useNavigate();
  const tabClickHandler = (num) => {
    if (num === 0) {
      navigate(url[urlKeys[num]]);
    }
    if (num === 1) {
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
