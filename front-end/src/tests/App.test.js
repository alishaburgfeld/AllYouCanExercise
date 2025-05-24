import { BrowserRouter } from "react-router";
import { render, screen } from "@testing-library/react";
import App from "../App";

test("renders Navbar text", () => {
  render(<App />);
  const navbarText = screen.getByText(/All You Can Exercise/i);
  expect(navbarText).toBeInTheDocument();
  expect(screen.getByRole("navigation")).toBeInTheDocument();
});
