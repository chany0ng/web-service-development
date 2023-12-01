import styles from './Header.module.scss';
import * as React from 'react';
import PropTypes from 'prop-types';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { postFetch } from './../config';
const Header = ({ sections, title }) => {
  const navigate = useNavigate();
  const logoutHandler = async () => {
    try {
      const response = await postFetch('api/logout', {});
      if (response.status === 200) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        navigate('/');
      } else {
        throw new Error('토큰이 정상적으로 삭제되지 않았습니다!');
      }
    } catch (error) {
      console.error('로그아웃 중 에러 발생:', error);
      alert(error);
    }
  };
  return (
    <header className={styles.header}>
      <Toolbar
        sx={{ borderBottom: 1, borderColor: 'primary.light' }}
        style={{ paddingLeft: '90px' }}
      >
        <div
          onClick={() => {
            navigate('/user/main');
          }}
          className={styles.logo}
        ></div>
        <Button
          variant="outlined"
          onClick={logoutHandler}
          size="small"
          className={styles.btn}
        >
          Logout
        </Button>
      </Toolbar>
      <Toolbar
        component="nav"
        variant="dense"
        sx={{
          justifyContent: 'space-around',
          overflowX: 'auto',
          padding: '10px',
          mb: '20px',
          borderBottom: 1,
          borderColor: 'primary.light',
          minHeight: '25px'
        }}
      >
        {sections.map((section) => (
          <Link key={section.title} to={section.url} className={styles.link}>
            {section.title}
          </Link>
        ))}
      </Toolbar>
    </header>
  );
};

Header.propTypes = {
  sections: PropTypes.arrayOf(
    PropTypes.shape({
      title: PropTypes.string.isRequired,
      url: PropTypes.string.isRequired
    })
  ).isRequired,
  title: PropTypes.string.isRequired
};

export default Header;
