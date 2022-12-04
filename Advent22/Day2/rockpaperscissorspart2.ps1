$score = 0

Get-Content .\input.txt | ForEach-Object {
Write-Host $_
#if he plays rock an I play paper
    if($_[2] -eq "Y")
    {
        if($_[0] -eq "A")
        {
            $score += 4
        }
        if($_[0] -eq "B")
        {
            $score += 5
        }
        if($_[0] -eq "C")
        {
            $score += 6
        }
    }
        if($_[2] -eq "X")
    {
        if($_[0] -eq "A")
        {
            $score += 3
        }
        if($_[0] -eq "B")
        {
            $score += 1
        }
        if($_[0] -eq "C")
        {
            $score += 2
        }
    }
        if($_[2] -eq "Z")
    {
        if($_[0] -eq "A")
        {
            $score += 8
        }
        if($_[0] -eq "B")
        {
            $score += 9
        }
        if($_[0] -eq "C")
        {
            $score += 7
        }
    }

}
Write-Host $score