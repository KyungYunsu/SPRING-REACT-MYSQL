import { create } from 'zustand';

interface BoardStore {
    title: string;
    content: string;
    boardImageFileLIst: File[];
    setTitle: (title: string) => void;
    setContent: (content: string) => void;
    setBoardImageFileLIst: (boardImageFileLIst: File[]) => void;
    resetBoard: () => void;
};

const useBoardStore = create<BoardStore>(set => ({
    title: '',
    content: '',
    boardImageFileLIst: [],
    setTitle: (title) => set(state => ({ ...state, title })),
    setContent: (content) => set(state => ({ ...state, content })),
    setBoardImageFileLIst: (boardImageFileLIst) => set(state => ({ ...state, boardImageFileLIst })),
    resetBoard: () => set(state => ({ ...state, title: '', content: '', boardImageFileLIst: []}))
}));

export default useBoardStore;