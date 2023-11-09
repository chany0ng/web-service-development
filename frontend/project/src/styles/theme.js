import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#00C853'
    },
    secondary: {
      main: '#303F9F'
    }
  },
  typography: {
    fontFamily: ['Noto Sans KR', 'sans-serif'].join(',')
  }
});

export default theme;
