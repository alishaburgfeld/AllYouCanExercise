import Homepage from "../pages/Homepage";
import { render, screen, fireEvent } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter, useNavigate } from "react-router-dom";

const mockedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => mockedNavigate,
}));

// jest.requireActual('react-router-dom') keeps all the real exports from react-router-dom, so you don’t accidentally mock out things you might need (like BrowserRouter, Route, etc.).

describe("Homepage", () => {
  beforeEach(() => {
    // Reset mock before each test
    mockedNavigate.mockReset();
    jest.clearAllMocks(); // Clear mocks to prevent test leakage
  });

  test("defaults to front image", () => {
    render(<Homepage />);
    const homepageImage = screen.getByAltText(/woman-front-body/i);
    expect(homepageImage).toBeInTheDocument();
  });

  test("defaults to front labels", () => {
    const frontLabels = [
      "SHOULDERS",
      "CHEST",
      "FOREARMS",
      "OBLIQUES",
      "QUADS",
      "ADDUCTORS",
      "ABS",
      "BICEPS",
      "CARDIO",
    ];
    render(<Homepage />);
    frontLabels.forEach((label) => {
      expect(screen.getByText(label)).toBeInTheDocument();
    });
  });

  test("Camera button is present", () => {
    render(<Homepage />);
    const cameraButton = screen.getByRole("button", {
      name: /camera-switch/i, // Matches the aria-label value
    });
    expect(cameraButton).toBeInTheDocument();
  });

  // test("HandleAngleSwitch function is called on camera button click", async () => {
  //   render(<Homepage />);
  //   // need to spy on the function, not mock it out since the function belongs inside my component.
  //   const handleAngleSwitchSpy = jest.spyOn(
  //     Homepage.prototype,
  //     "handleAngleSwitch",
  //   );
  //   const user = userEvent.setup();

  //   const cameraButton = screen.getByRole("button", {
  //     name: /camera-switch/i, // Matches the aria-label value
  //   });

  //   await user.click(cameraButton);
  //   expect(handleAngleSwitchSpy).toHaveBeenCalledTimes(1);
  //   handleAngleSwitchSpy.mockRestore();
  // });

  // This test fails because can't spy on functions in regular components...
  //   Ah, I see the issue now. The error you’re seeing (Property 'handleAngleSwitch' does not exist in the provided object) happens because handleAngleSwitch is defined inside the function component, and jest.spyOn is primarily used for spying on methods of class components or objects. For function components, you can't directly spy on the methods like you can with class methods.

  // Since handleAngleSwitch is a function defined inside the Homepage component, you’ll need to take a different approach. Specifically, you can’t spy on it directly, but you can still test that the function is invoked by checking the side effects it causes.

  test("Camera Button Click changes the image", async () => {
    render(<Homepage />);

    const cameraButton = screen.getByRole("button", {
      name: /camera-switch/i,
    });
    await userEvent.click(cameraButton);

    const backImage = screen.getByAltText(/woman-back-body/i);
    expect(backImage).toBeInTheDocument();
  });

  test("calls navigate with the correct path when label is clicked", async () => {
    const user = userEvent.setup();
    render(
      <MemoryRouter>
        <Homepage />
      </MemoryRouter>,
    );
    // rendered inside a MemoryRouter, which is necessary for routing-related tests, as it provides an in-memory history stack for routing (but doesn't actually change the URL).

    const chestButton = screen.getByRole("link", {
      name: /homepage_label_CHEST/i,
    });
    await user.click(chestButton);
    expect(mockedNavigate).toHaveBeenCalledTimes(1);
    expect(mockedNavigate).toHaveBeenCalledWith("/exercises/CHEST");
  });
});
