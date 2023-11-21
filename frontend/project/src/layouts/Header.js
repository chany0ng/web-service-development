import styles from './Header.module.scss';
import * as React from 'react';
import PropTypes from 'prop-types';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';

const Header = ({ sections, title }) => {
  return (
    <React.Fragment>
      <Toolbar
        sx={{ borderBottom: 1, borderColor: 'divider' }}
        style={{ paddingLeft: '90px' }}
      >
        <Typography
          component="h2"
          variant="h4"
          color="inherit"
          align="center"
          noWrap
          sx={{ flex: 1 }}
        >
          {title}
        </Typography>
        <Button variant="outlined" size="small">
          Logout
        </Button>
      </Toolbar>
      <Toolbar
        component="nav"
        variant="dense"
        sx={{
          justifyContent: 'space-around',
          overflowX: 'auto',
          mb: '20px',
          borderBottom: 1,
          borderColor: 'divider',
          minHeight: '25px'
        }}
      >
        {sections.map((section) => (
          <Link
            color="inherit"
            noWrap
            key={section.title}
            variant="body1"
            href={section.url}
            className={styles.link}
            sx={{
              p: 1,
              flexShrink: 0,
              fontSize: 'larger',
              textDecoration: 'none'
            }}
          >
            {section.title}
          </Link>
        ))}
      </Toolbar>
    </React.Fragment>
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
