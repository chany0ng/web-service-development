import styles from './css/CustomButton.module.min.css';
import { Button } from '@mui/material';
const CustomButton = () => {
  return (
    <Button
      variant="contained"
      className={styles.btn}
      sx={[
        {
          '&:hover': {
            color: 'white'
          }
        }
      ]}
    >
      This is button.
    </Button>
  );
};

export default CustomButton;
