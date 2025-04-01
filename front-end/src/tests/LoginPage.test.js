import LoginPage from "../pages/LoginPage";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

const mockedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => mockedNavigate,
}));

describe("LoginPage", () => {
  test("displays appropriate inputs", async () => {
    render(<LoginPage />);
    const usernameInput = screen.getByLabelText(/Username/i);
    const passwordInput = screen.getByRole("textbox", {
      name: /Password/i,
    });
    const submitButton = screen.getByRole("button", {
      name: /login/i,
    });
  });
});
