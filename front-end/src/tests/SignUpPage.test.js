import SignUpPage from "../pages/SignUpPage";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import axios from "axios";

jest.mock("axios");

const mockedNavigate = jest.fn();
const username = "username";
const password = "password";

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
    axios.post.mockResolvedValue("User registered successfully!");

    render(<SignUpPage />);
    // screen.debug();
    const usernameInput = screen.getByLabelText(/Username/i);
    const passwordInput = screen.getByTestId("signup-password-input");
    const confirmPasswordInput =
      screen.getByLabelText(/Confirm Password/i);
    const submitButton = screen.getByRole("button", {
      name: /sign up/i,
    });

    await userEvent.type(usernameInput, username);
    await userEvent.type(passwordInput, password);
    await userEvent.type(confirmPasswordInput, password);
    await userEvent.click(submitButton);
    expect(screen.queryByText(errorText)).toBeNull();

    expect(axios.post).toHaveBeenCalledWith(
      "http://localhost:8080/auth/register",
      { username, password },
      {
        headers: {
          "X-XSRF-TOKEN": undefined,
        },
        withCredentials: true, //
      },
    );
    expect(axios.post).toHaveBeenCalledTimes(1);
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

test("it does not submit the form when a field is missing", async () => {
  render(<SignUpPage />);
  // screen.debug();
  const usernameInput = screen.getByLabelText(/Username/i);
  const passwordInput = screen.getByTestId("signup-password-input");
  const submitButton = screen.getByRole("button", {
    name: /sign up/i,
  });

  await userEvent.type(usernameInput, username);
  await userEvent.click(submitButton);

  expect(axios.post).not.toHaveBeenCalledTimes(1);
});
