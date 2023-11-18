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
import { MenuItem } from '@mui/material';

const passwordQuestion = [
  {
    question: 'school',
    description: '졸업한 초등학교 이름'
  },
  {
    question: 'school2',
    description: '졸업한 중학교 이름'
  }
];

const FindPwPage = () => {
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
            <KeyOutlinedIcon fontSize="large" />
          </Avatar>
          <Typography component="h2" variant="h4">
            비밀번호 찾기
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="id"
                  label="Id"
                  type="id"
                  id="id"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="outlined-select-currency"
                  select
                  label="Password Question"
                  variant="standard"
                  helperText="비밀번호 찾기 질문을 선택해주세요"
                >
                  {passwordQuestion.map((option) => (
                    <MenuItem key={option.question} value={option.description}>
                      {option.description}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  variant="standard"
                  name="password-answer"
                  label="Password Answer"
                  type="password-answer"
                  id="password-answer"
                  helperText="비밀번호 찾기 답변을 작성해주세요"
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Search
            </Button>
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
