import Homepage from "../pages/Homepage";
import { render, screen, fireEvent } from "@testing-library/react";
import { MemoryRouter, useNavigate } from "react-router-dom";

const mockExercises = [
  { id: 1, exerciseGroup: "CHEST" },
  { id: 2, exerciseGroup: "BICEPS" },
];

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => jest.fn(),
}));
// jest.requireActual('react-router-dom') keeps all the real exports from react-router-dom, so you don’t accidentally mock out things you might need (like BrowserRouter, Route, etc.).

describe("Homepage", () => {
  test("defaults to front image", async () => {
    render(<Homepage />);
    const homepageImage = screen.getByAltText(/woman-front-body/i);
    expect(homepageImage).toBeInTheDocument();
  });

  test("Camera button is present", async () => {
    render(<Homepage />);
    const cameraButton = screen.getByRole("button", {
      name: /homepage_camera/i,
    });
    expect(cameraButton).toBeInTheDocument();
  });

  test("HandleAngleSwitch function called on camera button click", async () => {
    const mockHandleAngleSwitch = jest.fn();
    render(
      <Homepage mockHandleAngleSwitch={mockHandleAngleSwitch} />,
    );
    const cameraButton = screen.getByRole("button", {
      name: /homepage_camera/i,
    });
    fireEvent.click(cameraButton);
    expect(mockHandleAngleSwitch).toHaveBeenCalledTimes(1);
  });

  test("calls navigate with the correct path when label is clicked", () => {
    //   const handleLabelClick = (exerciseGroup) => {
    //     navigate(`/exercises/${exerciseGroup}`);
    // };
    const navigate = jest.fn();
    useNavigate.mockReturnValue(navigate);
    // Setting useNavigate inside the test with useNavigate.mockReturnValue(navigate) allows you to customize the behavior of useNavigate for each specific test, even though useNavigate is globally mocked with a default mock in jest.mock().
    render(
      <MemoryRouter>
        <Homepage />
      </MemoryRouter>,
    );
    // rendered inside a MemoryRouter, which is necessary for routing-related tests, as it provides an in-memory history stack for routing (but doesn't actually change the URL).

    const chestButton = screen.getByRole("button", {
      name: /homepage_label_CHEST/i,
    });
    fireEvent.click(chestButton);
    expect(navigate).toHaveBeenCalledTimes(1);
    expect(navigate).toHaveBeenCalledWith("/exercises/CHEST");
  });
});
