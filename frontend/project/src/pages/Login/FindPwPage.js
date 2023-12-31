import LoginBackground from '../../components/Background/LoginBackground';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import KeyOutlinedIcon from '@mui/icons-material/KeyOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { MenuItem, Alert } from '@mui/material';
import { useState, useEffect } from 'react';
import { postFetch } from '../../config';
import { useNavigate } from 'react-router-dom';
import { loginPageAuthCheck } from '../../AuthCheck';

const pwQuestionList = [
  {
    question: 'elementary',
    description: '졸업한 초등학교 이름',
    value: 1
  },
  {
    question: 'middle',
    description: '졸업한 중학교 이름',
    value: 2
  }
];

const FindPwPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    loginPageAuthCheck(navigate);
  }, []);
  const [inputData, setInputData] = useState({
    id: '',
    pw_question: 1,
    pw_answer: ''
  });
  const [isValid, setIsValid] = useState(true);
  const [validQuestion, setValidQuestion] = useState(false);
  const [newPw, setNewPw] = useState('');
  const newPwHandler = (e) => {
    setNewPw(e.target.value);
  };
  const onResetHandler = async (e) => {
    try {
      e.preventDefault();
      const response = await postFetch('api/auth/findPW/update', {
        id: inputData.id,
        new_password: newPw
      });
      if (response.status === 200) {
        alert('비밀번호가 재설정 되었습니다!');
        navigate('/signin');
      } else {
        throw new Error('비밀번호 재설정 오류발생');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  const inputDataHandler = (e) => {
    const key = e.target.id || 'pw_question';
    const value = e.target.value;
    setInputData((prevData) => ({ ...prevData, [key]: value }));
  };

  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      if (inputData.id.trim() === '' || inputData.pw_answer.trim() === '') {
        setIsValid(false);
      } else {
        setIsValid(true);
        const response = await postFetch('api/auth/findPW', inputData);
        if (response.status === 200) {
          // 변수 하나로 맞았는지 체크. 모달창에서 새로운 비밀번호 전송.
          setValidQuestion(true);
        } else {
          const data = await response.json();
          throw new Error(data.message);
        }
      }
    } catch (error) {
      console.error('비밀번호 찾기 에러', error);
      alert(error);
    }
  };
  return (
    <LoginBackground>
      <Container component="main" sx={{ width: '50vw' }}>
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
            <KeyOutlinedIcon fontSize="large" />
          </Avatar>
          <Typography component="h2" variant="h4">
            비밀번호 찾기
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  value={inputData.id}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="id"
                  label="아이디"
                  type="id"
                  id="id"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.pw_question}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  id="pw_question"
                  select
                  label="Password Question"
                  variant="standard"
                  helperText="비밀번호 찾기 질문을 선택해주세요"
                >
                  {pwQuestionList.map((option) => (
                    <MenuItem key={option.question} value={option.value}>
                      {option.description}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.pw_answer}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  variant="standard"
                  name="pw_answer"
                  label="Password Answer"
                  type="password-answer"
                  id="pw_answer"
                  helperText="비밀번호 찾기 답변을 작성해주세요"
                />
              </Grid>
              {validQuestion && (
                <Grid item xs={12}>
                  <TextField
                    value={newPw}
                    onChange={newPwHandler}
                    required
                    fullWidth
                    variant="standard"
                    name="new_password"
                    label="New Password"
                    type="password"
                    id="new_password"
                    helperText="새로운 비밀번호를 입력해주세요"
                  />
                </Grid>
              )}
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
                    모든 값을 입력해주세요
                  </Alert>
                </Grid>
              )}
            </Grid>
            {validQuestion ? (
              <Button
                onClick={onResetHandler}
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                비밀번호 재설정
              </Button>
            ) : (
              <Button
                onClick={onSubmitHandler}
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Search
              </Button>
            )}

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
                  to="/signin"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  로그인 하기
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </LoginBackground>
  );
};

export default FindPwPage;
