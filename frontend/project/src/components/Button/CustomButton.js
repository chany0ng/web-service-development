import styles from './css/CustomButton.module.min.css';
import { Button } from '@mui/material';
const CustomButton = ({ name, onClick }) => {
  return (
    <Button
      variant="outlined"
      className={styles.btn}
      onClick={onClick}
      sx={[
        {
          '&:hover': {
            color: '##008800'
          }
        },
        {
          width: '30%',
          margin: '0 auto',
          fontSize: '1.5rem',
          fontWeight: 'bold'
        }
      ]}
    >
      {name}
    </Button>
  );
};

export default CustomButton;
