import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#008000'
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
