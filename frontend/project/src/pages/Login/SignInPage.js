import { useState, useEffect } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import { Alert } from '@mui/material';
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import LoginBackground from '../../components/Background/LoginBackground';
import { postFetch } from '../../config';
import { useNavigate } from 'react-router-dom';
import { loginPageAuthCheck } from '../../AuthCheck';

const SignInPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    loginPageAuthCheck(navigate);
  }, []);

  const [inputData, setInputData] = useState({
    id: '',
    password: ''
  });
  const [isValid, setIsValid] = useState(true);
  const inputDataHandler = (e) => {
    e.preventDefault();
    const key = e.target.id;
    const value = e.target.value;
    setInputData((prevData) => ({ ...prevData, [key]: value }));
  };
  const idPwCheck = (data) => {
    const message = data.message;
    if (message.includes('아이디')) {
      alert('존재하지 않는 ID입니다.');
    }
    if (message.includes('비밀번호')) {
      alert('비밀번호가 일치하지 않습니다.');
    }
  };
  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      if (inputData.id.trim() === '' || inputData.password.trim() === '') {
        setIsValid(false);
      } else {
        setIsValid(true);
        const { status, data } = await postFetch('api/auth/login', inputData);
        if (status === 401) {
          idPwCheck(data);
        }
        if (status === 200) {
          localStorage.setItem('accessToken', data.accessToken);
          localStorage.setItem('refreshToken', data.refreshToken);
          navigate('/user/main');
        }
      }
    } catch (error) {
      console.error('Sign In 에러', error);
      alert(error);
    }
  };
  const googleLogin = async (e) => {
    try {
      e.preventDefault();
      const response = await fetch(
        'http://localhost:8080/oauth2/authorization/google'
      );
      console.log(response?.status);
      console.log(response?.data);
      console.log(response);
    } catch (error) {
      alert(error);
    }
  };

  return (
    <LoginBackground>
      <Container component="main" maxWidth="sm">
        <Box
          sx={{
            margin: 5,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center'
          }}
          style={{
            padding: '20px',
            border: '1px solid black',
            borderRadius: '10px',
            backgroundColor: '#ffffff'
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
            <LoginOutlinedIcon fontSize="large" />
          </Avatar>
          <Typography component="h2" variant="h4">
            로그인
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  value={inputData.id}
                  onChange={inputDataHandler}
                  fullWidth
                  name="id"
                  label="아이디"
                  type="id"
                  id="id"
                  variant="standard"
                  helperText="20자 이하"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.password}
                  fullWidth
                  onChange={inputDataHandler}
                  name="password"
                  label="비밀번호"
                  type="password"
                  id="password"
                  variant="standard"
                  helperText="20자 이하"
                />
              </Grid>

              {!isValid && (
                <Grid
                  item
                  xs={8}
                  sx={{ color: 'error.main', margin: '0 auto' }}
                >
                  <Alert
                    variant="outlined"
                    severity="error"
                    sx={{ fontSize: '1.3rem' }}
                  >
                    아이디와 비밀번호 모두 입력해주세요
                  </Alert>
                </Grid>
              )}
            </Grid>
            <Button
              type="submit"
              onClick={onSubmitHandler}
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Login
            </Button>
            <a
              href="http://localhost:8080/oauth2/authorization/google"
              onClick={googleLogin}
            >
              Google Login
            </a>
            <Grid container justifyContent="center">
              <Grid item xs={4}>
                <Link
                  to="/signup"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  회원가입
                </Link>
              </Grid>
              <Grid item xs={4}>
                <Link
                  to="/findpw"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  비밀번호 찾기
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </LoginBackground>
  );
};

export default SignInPage;
