document.addEventListener("DOMContentLoaded", () => {
  const fileInput = document.querySelector("input.file.single");
  const filesInput = document.querySelector("input.file.multi");
  const prevImg = document.querySelector("img.gallery");
  const imgBorderBox = document.querySelector("div.image");
  const base64Box = document.querySelector("textarea.base64");
  const multiImageBox = document.querySelector("div.image.multi");

  /*
  업로드할 이미지를 base64 방식으로 encoding 하는 함수
  base64 제약사항
  파일크기가 상당히 크다
  Jpeg 는 그래도 다소 양호하나 png 나 백터 타입 파일은 여러가지 이슈가 있다.
  파일의 크기문제로 업로드, DB 저장등에서 문제를 일으킬수 있다
  다만, DB 에 파일을 저장하므로써 
      별도의 이미지를 보관하는 방식에 비해 다소 유리한 점도 있다.
  
  base64 로 변환된 파일을 압축하여, jpeg 로 변환하면 용량문제를 다소 해결할수 있다.

  */

  const encodeImageFileAsBase64 = async (image) => {
    return new Promise((resoleve, _) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        resoleve(reader.result);
      };
      reader.readAsDataURL(image);
    });
  };

  // prevImg.addEventListener("click", () => fileInput.click());
  prevImg.addEventListener("click", () => filesInput.click());

  imgBorderBox.addEventListener("paste", async (e) => {
    const items = e.clipboardData.items;
    // images 객체가 존재하면 그중 0번째 요소를 getter 하기
    const item = items?.[0];
    // image.png, text/plan, application/text,
    // 붙여넣기 한 데이터가 image/* 이면~~
    if (item.type.indexOf("image") === 0) {
      // 붙어넣기 한 이미지 중에서 파일만 추출하기
      const blob = item.getAsFile();
      console.log(blob);
      // 파일이 추출이 안되면
      if (!blob) {
        return null;
      }
      const base64 = await encodeImageFileAsBase64(blob);
      prevImg.src = base64;
      base64Box.value = base64;
    } else {
      alert("이미지만 붙여 넣기 하세요");
    }
  });

  const filePreView = async (file) => {
    const base64 = await encodeImageFileAsBase64(file);
    if (base64) {
      const img = document.createElement("img");
      img.style.width = "100px";
      img.src = base64;
      multiImageBox.appendChild(img);
    }

    // prevImg.src = base64;
    // base64Box.value = base64;
  };

  filesInput.addEventListener("change", async (e) => {
    const files = e.target.files;
    multiImageBox.innerHTML = "";
    for (let file of files) {
      await filePreView(file);
    }
  });
  fileInput.addEventListener("change", async (e) => {
    const target = e.target;
    const file = target.files[0];
    await filePreView(file);

    // const base64 = await encodeImageFileAsBase64(file);
    // prevImg.src = base64;
    // base64Box.value = base64;
  });
});
