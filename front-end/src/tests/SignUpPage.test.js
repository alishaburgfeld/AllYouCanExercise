import SignUpPage from "../pages/SignUpPage";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

const mockedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => mockedNavigate,
}));

describe("SignUpPage", () => {
  beforeEach(() => {
    mockedNavigate.mockReset();
    jest.clearAllMocks();
  });

  test("submits form with correct information and does not display error text", async () => {
    const errorText = "Passwords do not match!";
    render(<SignUpPage />);
    const usernameInput = screen.getByLabelText(/Username/i);
    const passwordInput = screen.getByTestId("signup-password-input");
    const confirmPasswordInput =
      screen.getByLabelText(/Confirm Password/i);
    const submitButton = screen.getByRole("button", {
      name: /sign up/i,
    });

    // screen.debug();

    await userEvent.type(usernameInput, "username");
    await userEvent.type(passwordInput, "password");
    await userEvent.type(confirmPasswordInput, "password");
    await userEvent.click(submitButton);
    expect(screen.queryByText(errorText)).toBeNull();
    // add in logic to test axios call being sent.
  });

  test("displays error when passwords do not pass", async () => {
    const errorText = "Passwords do not match!";
    render(<SignUpPage />);
    const usernameInput = screen.getByLabelText(/Username/i);
    const passwordInput = screen.getByTestId("signup-password-input");
    const confirmPasswordInput =
      screen.getByLabelText(/Confirm Password/i);
    const submitButton = screen.getByRole("button", {
      name: /sign up/i,
    });

    // screen.debug();

    await userEvent.type(usernameInput, "username");
    await userEvent.type(passwordInput, "password");
    await userEvent.type(confirmPasswordInput, "incorrect-password");
    await userEvent.click(submitButton);
    expect(screen.getByText(errorText)).toBeInTheDocument();
  });
});
