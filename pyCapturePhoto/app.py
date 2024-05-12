import cv2
import os

def capture_and_save(frame, counter, save_path):
    img_name = os.path.join(save_path, "opencv_frame_{}.png".format(counter))
    cv2.imwrite(img_name, frame)
    print(img_name)

def main():
    cam = cv2.VideoCapture(0)
    cv2.namedWindow("Capture Image")

    img_counter = 0

    while True:
        ret, frame = cam.read()

        if not ret:
            print("Failed to grab frame")
            break

        cv2.imshow("Capture Image", frame)

        key = cv2.waitKey(1)

        if key == 27:  # Escape key
            print("Escape hit, closing the app")
            break
        elif key == ord('s'):  # Ctrl + S
            save_directory = "/home/shen/Documents/myProject/NewManazinePrison/src/main/resources/VisitorDpStore"
            capture_and_save(frame, img_counter, save_directory)
            img_counter += 1
            break

    cam.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
