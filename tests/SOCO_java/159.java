



 


class BasicAuth {

    public BasicAuth() {}


    private static byte[] cvtTable = {
        (byte)'A', (byte)'B', (byte)'C', (byte)'D', (byte)'E',
        (byte)'F', (byte)'G', (byte)'H', (byte)'I', (byte)'J',
        (byte)'K', (byte)'L', (byte)'M', (byte)'N', (byte)'O',
        (byte)'P', (byte)'Q', (byte)'R', (byte)'S', (byte)'T',
        (byte)'U', (byte)'V', (byte)'W', (byte)'X', (byte)'Y',
        (byte)'Z',
        (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e',
        (byte)'f', (byte)'g', (byte)'h', (byte)'i', (byte)'j',
        (byte)'k', (byte)'l', (byte)'m', (byte)'n', (byte)'o',
        (byte)'p', (byte)'q', (byte)'r', (byte)'s', (byte)'t',
        (byte)'u', (byte)'v', (byte)'w', (byte)'x', (byte)'y',
        (byte)'z',
        (byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4',
        (byte)'5', (byte)'6', (byte)'7', (byte)'8', (byte)'9',
        (byte)'+', (byte)'/'
    };

    static String encode(String name,
                         String passwd) {
        byte input[] = (name + ":" + passwd).getBytes();
        byte[] output = new byte[((input.length / 3) + 1) * 4];
        int ridx = 0;
        int chunk = 0;

        for (int i = 0; i < input.length; i += 3) {
            int left = input.length - i;


            if (left > 2) {
                chunk = (input[i] << 16)|
                        (input[i + 1] << 8) |
                         input[i + 2];
                output[ridx++] = cvtTable[(chunk&0xFC0000)>>18];
                output[ridx++] = cvtTable[(chunk&0x3F000) >>12];
                output[ridx++] = cvtTable[(chunk&0xFC0)   >> 6];
                output[ridx++] = cvtTable[(chunk&0x3F)];
            } else if (left == 2) {

                chunk = (input[i] << 16) |
                        (input[i + 1] << 8);
                output[ridx++] = cvtTable[(chunk&0xFC0000)>>18];
                output[ridx++] = cvtTable[(chunk&0x3F000) >>12];
                output[ridx++] = cvtTable[(chunk&0xFC0)   >> 6];
                output[ridx++] = '=';
            } else {

                chunk = input[i] << 16;
                output[ridx++] = cvtTable[(chunk&0xFC0000)>>18];
                output[ridx++] = cvtTable[(chunk&0x3F000) >>12];
                output[ridx++] = '=';
                output[ridx++] = '=';
            }
        }
        return new String(output);
    }
}