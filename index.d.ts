declare var Pax: {
  FULL_CUT: number;
  PARTIAL_CUT: number;

  printStr: (text: string, cutMode?: number) => void;
  printText: (
    text: string,
    grayLevel: number,
    large: boolean,
    lineSpace: number,
    invert?: boolean
  ) => void;
  printQRCode: (text: string) => void;
  printBarcode: (text: string) => void;
  start: () => void;
  init: () => void;
  openDrawer: () => Promise<any>;
  printBitmap: (inputValue: string, cutMode?: number) => void;
};

export default Pax;
