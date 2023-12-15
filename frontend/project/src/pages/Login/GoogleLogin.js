import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './GoogleLogin.module.scss';
const GoogleLogin = () => {
  // query로 넘어온 액세스토큰과 리프레시토큰을 저장한다.
  // 그리고 바로 메인으로 이동한다.
  const navigate = useNavigate();
  useEffect(() => {
    const params = new URL(document.location.toString()).searchParams;
    // const params = new URLSearchParams(window.location.search);
    const accessToken = params.get('accessToken');
    const refreshToken = params.get('refreshToken');
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
    navigate('/user/main');
  });
  return (
    <section className={styles['dots-container']}>
      <div className={styles.dot}></div>
      <div className={styles.dot}></div>
      <div className={styles.dot}></div>
      <div className={styles.dot}></div>
      <div className={styles.dot}></div>
    </section>
  );
};

export default GoogleLogin;
