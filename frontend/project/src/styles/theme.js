import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  primary: {
    main: '#90ee02'
  },
  secondary: {
    main: '#6002ee'
  },
  typography: {
    fontFamily: ['Noto Sans KR', 'sans-serif'].join(',')
  }
});

export default theme;
