// src/components/common/PageHeader.jsx
import { Box, Typography, IconButton } from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import { useTheme } from "@mui/material/styles";
import { useNavigate } from "react-router-dom";
import "../../css/ExercisePage.css"


export default function Header({
  title,                   
  icon,                  
  onIconClick,            
  rightContent = null,     // optional JSX (search bar, buttons, etc.)
  fixed = true,
  typographyClassName
}) {
  const theme = useTheme();
  const navigate = useNavigate();
    console.log('header rendered')
  return (
    <Box
      sx={{
        position: fixed ? "fixed" : "relative",
        top: 55,
        left: 0,
        right: 0,
        bgcolor: "background.default",
        borderBottom: "1px solid",
        borderColor: "divider",
        zIndex: 1000,
        py: 1.5,
        px: { xs: 2, sm: 3, lg: 4 },
      }}
    >
      <Box
        sx={{
          position: "relative",
          maxWidth: "1280px",
          mx: "auto",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {(icon) && (
          <IconButton
            onClick={onIconClick}
            sx={{
              position: "absolute",
              left: 0,
              color: theme.palette.secondary.main,
              "&:hover": { opacity: 0.8 },
            }}
          >
            {icon}
          </IconButton>
        )}

        <Typography
          sx={{
            className: {typographyClassName},
            fontSize: "1.8rem",
            color: theme.palette.secondary.main,
            textWrap: "balance",
          }}
        >
          {title}
        </Typography>

        {rightContent && (
          <Box sx={{ position: "absolute", right: 0 }}>{rightContent}</Box>
        )}
      </Box>
    </Box>
  );
}
