import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#008000',
      light: '#4caf50'
    },
    secondary: {
      main: '#303F9F',
      light: '#0288d1'
    },
    error: {
      main: '#d32f2f'
    }
  },
  typography: {
    fontFamily: ['Noto Sans KR', 'GmarketSans', 'sans-serif'].join(',')
  }
});

export default theme;
