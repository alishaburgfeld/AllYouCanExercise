import { getImageSource } from "../utils/HelperFunctions";

describe("getImageSource", () => {
  test("happy path", () => {
    const name = "Dumbbell Overhead Press";
    const response = getImageSource(name);
    expect(response).toEqual(
      "/images/SHOULDERS/Dumbbell Overhead Press.png",
    );
  });
  test("unhappy path", () => {
    const name = "Raise Elliot to the Roof";

    const response = getImageSource(name);

    expect(response).toEqual("/images/noexerciseimage.png");
  });
});
