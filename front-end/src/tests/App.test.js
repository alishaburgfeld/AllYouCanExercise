import { BrowserRouter } from "react-router";
import { render, screen } from "@testing-library/react";
import App from "../../../front-end-old/src/App";

test("renders Navbar text", () => {
  render(
    <BrowserRouter>
      <App />
    </BrowserRouter>
  );
  const navbarText = screen.getByText(/All You Can Exercise/i);
  expect(navbarText).toBeInTheDocument();
  expect(screen.getByRole("navigation")).toBeInTheDocument();
});

// https://www.npmjs.com/package/react-router-dom
// https://stackoverflow.com/questions/79256904/cannot-detect-installed-react-router-dom-package-when-running-unit-tests
