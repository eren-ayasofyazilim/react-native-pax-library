declare var Pax: {
	FULL_CUT: number;
	PARTIAL_CUT: number;

	printStr: (text: string, cutMode?: number) => void;
	printText: (text: string, grayLevel:number, cutMode?: number) => void;
	openDrawer: () => Promise<any>;
	printBitmap: (inputValue: string, cutMode?: number) => void;
};

export default Pax;
