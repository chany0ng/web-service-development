import styles from './PostView.module.scss';
import CustomButton from '../Button/CustomButton';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { postFetch } from '../../config';
import { useState, useEffect } from 'react';
import { userInfo } from '../../recoil';
import { useRecoilState } from 'recoil';
const PostView = ({ data, moveToList, isBoard }) => {
  const navigate = useNavigate();
  const [user, setUser] = useRecoilState(userInfo);
  console.log(user);
  const [content, setContent] = useState('');
  const addCommentHandler = async () => {
    try {
      console.log(content);
      const response = await postFetch(
        `api/board/comment/create/${data.board_id}`,
        { content: content }
      );
      if (response.status === 200) {
        window.location.reload();
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('댓글 작성 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  const commentDeleteHandler = async (id) => {
    try {
      const response = await postFetch(`api/board/comment/delete`, {
        board_id: data.board_id,
        comment_id: id
      });
      if (response.status === 200) {
        window.location.reload();
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('댓글 삭제 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  const deleteHandler = async () => {
    try {
      const boardOrNotice = !isBoard
        ? {
            notice_id: data.notice_id
          }
        : {
            board_id: data.board_id
          };
      const api = !isBoard ? 'api/admin/notice/delete' : 'api/board/delete';
      const response = await postFetch(api, boardOrNotice);
      if (response.status === 200) {
        navigate(-1);
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('게시물 삭제 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  const realDate = new Date(data.date).toLocaleDateString();
  return (
    <article className={styles.article}>
      <div className={styles.first}>
        <h2 style={{ textAlign: 'left', fontSize: '2.5rem' }}>
          {data.title}
          {data.author && (
            <Button
              variant="contained"
              color="primary"
              sx={{
                fontSize: '1rem',
                width: '5vw',
                margin: '10px',
                float: 'right'
              }}
              onClick={deleteHandler}
            >
              글 삭제
            </Button>
          )}
        </h2>
        <section style={{ display: 'flex', fontSize: '1.2rem' }}>
          <p style={{ textAlign: 'left' }}>작성자: {data.user_id}</p>
          <p style={{ textAlign: 'left' }}>등록일: {realDate}</p>
          <p style={{ textAlign: 'left' }}>조회수: {data.views}</p>
        </section>
      </div>
      <div className={styles.second}>{data.content}</div>
      <section style={{ marginBottom: '10vh' }}>
        {data.comments?.map((comment, index) => (
          <div
            key={index}
            style={{ display: 'flex', marginBottom: '10px' }}
            className={styles.comment}
          >
            <div style={{ width: '20%', textAlign: 'left' }}>
              {comment.user_id}
            </div>
            <div style={{ width: '70%', textAlign: 'left' }}>
              {comment.content}
            </div>

            <div style={{ width: '10%' }}>
              {comment.date}
              {user.user_id === comment.user_id && (
                <button
                  onClick={() => commentDeleteHandler(comment.comment_id)}
                >
                  삭제
                </button>
              )}
            </div>
          </div>
        ))}
        {isBoard && (
          <p style={{ textAlign: 'left', fontSize: 'larger', margin: '5px' }}>
            댓글 쓰기
          </p>
        )}
        {isBoard && (
          <div className={styles.write}>
            <div style={{ width: '90%' }}>
              <textarea
                value={content}
                onChange={(e) => setContent(e.target.value)}
              />
            </div>
            <button onClick={addCommentHandler}>작성</button>
          </div>
        )}
      </section>
      <CustomButton name="목록" onClick={moveToList} />
    </article>
  );
};

export default PostView;
