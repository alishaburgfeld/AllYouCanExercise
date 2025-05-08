import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      // grey
      main: "#5c5861",
      light: "#BDBDBD", // Light grey
      dark: "#7d6b60", // Dark grey
      mix: "#918279",
    },
    // orange
    secondary: {
      main: "#FF5733",
      dark: "#eb330c",
    },
  },
  fontSize: {
    primary: [".9rem", "1.2rem", "1.4rem"],
    secondary: ["1.9rem", "1.9rem", "4rem"],
  },
});

export default theme;
